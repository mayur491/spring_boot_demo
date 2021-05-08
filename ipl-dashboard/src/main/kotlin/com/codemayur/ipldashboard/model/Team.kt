package com.codemayur.ipldashboard.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Transient

@Entity
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,
    var teamName: String = "",
    var totalMatches: Long = 0L,
    var totalWins: Long = 0L,
    @Transient
    var matches: List<Match>
) {
    constructor(teamName: String, totalMatches: Long) : this(
        // id = 0L,
        teamName = teamName,
        totalMatches = totalMatches,
        totalWins = 0L,
        matches = listOf()
    )
}