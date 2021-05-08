package com.codemayur.ipldashboard.data

object MatchInputConstants {
    const val TOSS_DECISION_BAT = "bat"
}

data class MatchInput(
    var id: String = "",
    var city: String = "",
    var date: String = "",
    var player_of_match: String = "",
    var venue: String = "",
    var neutral_venue: String = "",
    var team1: String = "",
    var team2: String = "",
    var toss_winner: String = "",
    var toss_decision: String = "",
    var match_winner: String = "",
    var result: String = "",
    var result_margin: String = "",
    var eliminator: String = "",
    var method: String = "",
    var umpire1: String = "",
    var umpire2: String = ""
)