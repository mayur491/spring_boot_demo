package com.codemayur.ipldashboard.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Match(
    @Id
    var id: Long = 0L,
    var city: String = "",
    var date: LocalDate = LocalDate.MIN,
    var playerOfMatch: String = "",
    var venue: String = "",
    var team1: String = "",
    var team2: String = "",
    var tossWinner: String = "",
    var tossDecision: String = "",
    var matchWinner: String = "",
    var result: String = "",
    var resultMargin: String = "",
    var umpire1: String = "",
    var umpire2: String = ""
)