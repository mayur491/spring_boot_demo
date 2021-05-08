package com.codemayur.ipldashboard.data

import com.codemayur.ipldashboard.model.Team
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.transaction.Transactional


@Component
class JobCompletionNotificationListener(
    private val entityManager: EntityManager
) : JobExecutionListenerSupport() {

    val logger: Logger = LoggerFactory.getLogger(JobCompletionNotificationListener::class.java)

    @Transactional
    override fun afterJob(jobExecution: JobExecution) {

        if (BatchStatus.COMPLETED === jobExecution.status) {

            logger.info("JOB FINISHED! Time to verify the results")

            /*val query = "SELECT team1, team2, date FROM match"
            jdbcTemplate
                .query(query) { rs, row ->
                    "Team1: " + rs.getString(1) + " Team2: " + rs.getString(2) + " Date: " + rs.getString(
                        3
                    )
                }
                .forEach { str -> println("$str") }*/

            // SELECT DISTINCT team1 FROM match m UNION SELECT DISTINCT team2 FROM match m
            // UNION doesn't work in JPA, so we'll have to do it twice

            val teamData: MutableMap<String, Team> = HashMap()

            /*
            TEAM 1
             */
            val resultsOfTeam1: MutableList<Array<Any>> =
                entityManager.createQuery("SELECT m.team1, COUNT(*) FROM Match m GROUP BY m.team1")
                    .resultList as MutableList<Array<Any>>

            resultsOfTeam1
                .map { Team(it[0] as String, it[1] as Long) }
                .forEach { team -> teamData[team.teamName] = team }

            /*
            TEAM 2
             */
            val resultsOfTeam2: MutableList<Array<Any>> =
                entityManager.createQuery("SELECT m.team2, COUNT(*) FROM Match m GROUP BY m.team2")
                    .resultList as MutableList<Array<Any>>

            resultsOfTeam2
                .forEach {
                    val team: Team? = teamData[it[0] as String]
                    if (team != null) team.totalMatches = (team.totalMatches) + (it[1] as Long)
                    else teamData[it[0] as String] = Team(it[0] as String, it[1] as Long)
                }

            /*
            Winners
             */
            val resultsOfWinners: MutableList<Array<Any>> =
                entityManager.createQuery("SELECT m.matchWinner, COUNT(*) FROM Match m GROUP BY m.matchWinner")
                    .resultList as MutableList<Array<Any>>

            resultsOfWinners
                .forEach {
                    val team: Team? = teamData[it[0] as String]
                    if (team != null) team.totalWins = it[1] as Long
                    else logger.error("EC:CCIDJCNLafterJob_01 Invalid Team '${it[0] as String}'")
                }

            /*
            Persist teamData
             */
            teamData.values
                .forEach { team -> entityManager.persist(team) }

            teamData.values
                .forEach { println(it) }
        }
    }
}