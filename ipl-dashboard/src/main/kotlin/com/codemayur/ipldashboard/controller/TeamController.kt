package com.codemayur.ipldashboard.controller

import com.codemayur.ipldashboard.model.Match
import com.codemayur.ipldashboard.repository.MatchRepository
import com.codemayur.ipldashboard.repository.TeamRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.Month

/**
 * APIs
 * 1. GET /team/<team_name>
 * 2. GET /team/<team_name>/match?year=<year>
 *
 * @author Mayur Somani
 */
@RestController
@RequestMapping("/team")
@CrossOrigin
class TeamController(
    private val teamRepository: TeamRepository,
    private val matchRepository: MatchRepository
) {

    @GetMapping("/{teamName}")
    fun getTeamByName(@PathVariable(required = true) teamName: String): ResponseEntity<Any> {
        val teamOptional = teamRepository.findByTeamName(teamName)
        if (teamOptional.isEmpty) return teamNameNotFound(teamName)
        val team = teamOptional.get()
        team.matches = getRecentMatchesOfTeam(teamName, 4)
        val map: MutableMap<String, Any> = hashMapOf()
        map["team"] = team
        map["success"] = true
        return ResponseEntity.ok(map)
    }

    /*
     * If size=3, this function will return the latest 3 matches of the team
     */
    private fun getRecentMatchesOfTeam(teamName: String, size: Int): List<Match> {
        val pageable = PageRequest.of(0, size)
        return matchRepository.getByTeam1OrTeam2OrderByDateDesc(
            teamName,
            teamName,
            pageable
        )
    }

    private fun teamNameNotFound(teamName: String): ResponseEntity<Any> {
        val map: MutableMap<String, Any> = hashMapOf()
        map["success"] = false
        map["message"] = "Team '$teamName' not found"
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(map)
    }

    @GetMapping("/{teamName}/matches")
    fun getMatchesForTeam(
        @PathVariable(required = true) teamName: String,
        @RequestParam(required = true) year: Int
    ): ResponseEntity<Any> {
        val fromDate = LocalDate.of(year, Month.JANUARY, 1)
        val toDate = LocalDate.of(year + 1, Month.JANUARY, 1)
        val matches: List<Match> = matchRepository.getMatchesByTeamBetweenDates(
            teamName,
            fromDate,
            toDate
        )
        val map: MutableMap<String, Any> = hashMapOf()
        map["matches"] = matches
        map["success"] = true
        return ResponseEntity.ok(map)
    }

}