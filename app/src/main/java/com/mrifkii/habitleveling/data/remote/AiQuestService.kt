package com.mrifkii.habitleveling.data.remote

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named

class AiQuestService @Inject constructor(
    @param:Named("gemini_api_key") private val apiKey: String
) {
    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey,
        generationConfig = generationConfig {
            // Biarkan kosong dulu biar kompatibel
        }
    )

    suspend fun generateQuests(player: Player, previousQuests: List<Quest>): List<Quest> {
        val prompt = """
            You are the 'System' (Architect) from Solo Leveling. Your job is to strictly monitor and provide challenging daily quests for the player.
            
            PLAYER CURRENT STATUS:
            - Name: ${player.name}
            - Title: ${player.title}
            - Rank: ${player.rank}
            - Level: ${player.level}
            - Stats: STR:${player.stats.strength}, VIT:${player.stats.vitality}, AGI:${player.stats.agility}, INT:${player.stats.intelligence}, PER:${player.stats.perception}
            
            QUEST HISTORY:
            ${previousQuests.joinToString { "${it.title}: ${it.description} (${if (it.isCompleted) "COMPLETED" else "FAILED"})" }}
            
            INSTRUCTIONS:
            1. CHECK FOR RANK UP: 
               - If Rank E and Level >= 25
               - If Rank D and Level >= 35
               - If Rank C and Level >= 50
               - If Rank B and Level >= 75
               - If Rank A and Level >= 90
               IF ANY ABOVE IS TRUE: The player is at a LEVEL CAP. You MUST generate ONE 'RANK_UP' quest in the set. This quest should be very difficult and epic.
            
            2. Generate 4 NEW Quests.
            3. SCALING: The difficulty MUST scale with the player's Level and Stats. 
               - If STR is low, provide strength-building quests.
               - If Level is high, increase quantity significantly (e.g., Level 1: 100 push-ups, Level 10: 250 push-ups).
            4. REWARDS:
               - RewardXp: Scale with difficulty (Higher level = more XP required).
               - RewardGold: Higher rank/level should grant significantly more gold.
               - RewardStatPoints: Rare! Only give 1 stat point for the most difficult quest in the set.
            5. TONE: Immersive, RPG-style instructions.
            
            OUTPUT FORMAT:
            Return ONLY a raw JSON array. Fields:
            - id: unique string (e.g., 'gen_daily_001')
            - title: short epic name
            - description: clear instruction with EXACT quantity
            - type: string (Either 'DAILY' or 'RANK_UP')
            - rewardXp: float
            - rewardGold: integer
            - rewardStatPoints: integer
            
            No markdown, no explanation. Just the JSON array.
        """.trimIndent()

        Log.d("AiQuestService", "SENDING PROMPT: $prompt")

        return try {
            val response = model.generateContent(prompt)
            val jsonText = response.text?.trim() ?: return emptyList()

            Log.d("AiQuestService", "RAW RESPONSE: $jsonText")

            // Bersihkan format markdown jika AI nakal tetep ngasih ```json
            val cleanJson = jsonText.removeSurrounding("```json", "```").trim()

            val json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
            val generatedQuests = json.decodeFromString<List<QuestData>>(cleanJson)
            generatedQuests.map {
                Quest(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    type = if (it.type?.uppercase() == "RANK_UP") QuestType.RANK_UP else QuestType.DAILY,
                    rewardXp = it.rewardXp,
                    rewardGold = it.rewardGold,
                    rewardStatPoints = it.rewardStatPoints
                )
            }
        } catch (e: Exception) {
            when (e.cause) {
                is UnknownHostException -> {
                    throw IOException("No Internet Connection")
                }
                is SocketTimeoutException -> {
                    throw IOException("Request Timeout")
                }
                else -> {
                    throw Exception(e.message ?: "Unknown AI Error")
                }
            }
        }
    }
}

@kotlinx.serialization.Serializable
data class QuestData(
    val id: String,
    val title: String,
    val description: String,
    val type: String? = "DAILY",
    val rewardXp: Float,
    val rewardGold: Int,
    val rewardStatPoints: Int
)
