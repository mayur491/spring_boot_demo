import React from 'react'
import { Link } from 'react-router-dom'
import './MatchSmallCard.css'

const MatchSmallCard = ({ teamName, match }) => {

    if (!match) return null

    const otherTeamName = match.team1 === teamName ? match.team2 : match.team1
    const otherteamRoute = `/teams/${otherTeamName}`

    const isMatchWon = teamName === match.matchWinner;

    return (
        <div className={isMatchWon ? 'MatchSmallCard won-card' : 'MatchSmallCard lost-card'}>
            <span className='vs'>vs</span>
            <h1>
                <Link to={otherteamRoute}>
                    {otherTeamName}
                </Link>
            </h1>
            <p className='match-result'>
                {match.matchWinner} won by {match.resultMargin} {match.result}
            </p>
        </div >
    )
}

export default MatchSmallCard
