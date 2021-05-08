package com.codemayur.ipldashboard.data

import com.codemayur.ipldashboard.model.Match
import org.springframework.batch.item.ItemProcessor
import java.time.LocalDate

class MatchDataProcessor : ItemProcessor<MatchInput, Match> {

    @Override
    override fun process(matchInput: MatchInput): Match {

        val match = Match()

        match.id = matchInput.id.toLong()
        match.city = matchInput.city
        match.date = LocalDate.parse(matchInput.date)
        match.playerOfMatch = matchInput.player_of_match
        match.venue = matchInput.venue

        /*
        Set Team1 and Team2 depending on Team Order
        Team1 = Team who bat in first innings
        Team2 = Other team
        */
        val firstInningsTeam: String
        val secondInningsTeam: String
        if (MatchInputConstants.TOSS_DECISION_BAT == matchInput.toss_decision) {
            firstInningsTeam = matchInput.toss_winner
            secondInningsTeam = if (matchInput.toss_winner == matchInput.team1) matchInput.team2 else matchInput.team1
        } else {
            secondInningsTeam = matchInput.toss_winner
            firstInningsTeam = if (matchInput.toss_winner == matchInput.team1) matchInput.team2 else matchInput.team1
        }
        match.team1 = firstInningsTeam
        match.team2 = secondInningsTeam

        match.tossWinner = matchInput.toss_winner
        match.matchWinner = matchInput.match_winner
        match.result = matchInput.result
        match.resultMargin = matchInput.result_margin
        match.umpire1 = matchInput.umpire1
        match.umpire2 = matchInput.umpire2

        return match
    }

}