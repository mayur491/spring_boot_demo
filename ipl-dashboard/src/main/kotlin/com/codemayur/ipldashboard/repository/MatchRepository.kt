package com.codemayur.ipldashboard.repository

import com.codemayur.ipldashboard.model.Match
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MatchRepository : CrudRepository<Match, Long> {

    fun getByTeam1OrTeam2OrderByDateDesc(
        teamName1: String,
        teamName2: String,
        pageable: Pageable
    ): List<Match>

    // TODO: Want to make this method a default method as in JAVA
    /*fun findLatestMatchesOfTeam(teamName: String, count: Int): List<Match> {
        val pageable: Pageable = PageRequest.of(0, count)
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable)
    }*/

    /*fun getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
        teamName1: String,
        date1: LocalDate,
        date2: LocalDate,
        teamName2: String,
        date3: LocalDate,
        date4: LocalDate
    ): List<Match>*/

    @Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and m.date between :fromDate and :toDate order by date desc")
    fun getMatchesByTeamBetweenDates(
        @Param("teamName") teamName: String,
        @Param("fromDate") fromDate: LocalDate,
        @Param("toDate") toDate: LocalDate
    ): List<Match>

}