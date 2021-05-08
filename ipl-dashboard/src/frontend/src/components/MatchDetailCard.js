import React from 'react'
import { Link } from 'react-router-dom'

const MatchDetailCard = ({ teamName, match }) => {

    if (!match) return null

    const otherTeamName = match.team1 === teamName ? match.team2 : match.team1

    const otherteamRoute = `/teams/${otherTeamName}`

    return (
        <div className='MatchDetailCard'>
            <h2>Latest Matches</h2>
            <h3>Match Details</h3>
            <hr />
            <h1>
                <i> vs </i>
                <Link to={otherteamRoute}>
                    {otherTeamName}
                </Link>
            </h1>
            <h2>on {match.date}</h2>
            <h3>at {match.venue} in {match.city}</h3>
            <h4>{match.matchWinner} won by {match.resultMargin} {match.result}</h4>
            <hr />
        </div>
    )
}

export default MatchDetailCard
