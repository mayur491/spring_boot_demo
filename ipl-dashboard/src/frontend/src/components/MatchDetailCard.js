import React from 'react'
import { Link } from 'react-router-dom'
import './MatchDetailCard.css'

const MatchDetailCard = ({ teamName, match }) => {

    if (!match) return null

    const otherTeamName = match.team1 === teamName ? match.team2 : match.team1

    const otherteamRoute = `/teams/${otherTeamName}`

    const isMatchWon = teamName === match.matchWinner;

    return (
        <div className={isMatchWon ? 'MatchDetailCard won-card' : 'MatchDetailCard lost-card'}>
            {/* Row 1 starts */}
            <div>
                <span className='vs'>vs</span>
                <h1>
                    <Link to={otherteamRoute}>
                        {otherTeamName}
                    </Link>
                </h1>
                <h2 className='match-date'>on {match.date}</h2>
                <h3 className='match-venue'>at {match.venue} in {match.city}</h3>
                <p className='match-result'>{match.matchWinner} won by {match.resultMargin} {match.result}</p>
            </div>
            {/* Row 1 ends */}

            {/* Row 2 starts */}
            <div className='additional-detail'>
                <h3>First Innings</h3>
                <p>{match.team1}</p>
                <h3>Second Innings</h3>
                <p>{match.team2}</p>
                <h3>Man of the match</h3>
                <p>{match.playerOfMatch}</p>
                <h3>Umpires</h3>
                <p>{match.umpire1}, {match.umpire2}</p>
            </div>
            {/* Row 2 ends */}
        </div>
    )
}

export default MatchDetailCard
