package com.codemayur.ipldashboard.repository

import com.codemayur.ipldashboard.model.Team
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeamRepository: CrudRepository<Team, Long> {

    fun findByTeamName(teamName: String): Optional<Team>

}