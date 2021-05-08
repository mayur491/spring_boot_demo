import React from 'react'
import { Link } from 'react-router-dom'

const MatchSmallCard = ({ teamName, match }) => {

    if (!match) return null

    const otherTeamName = match.team1 === teamName ? match.team2 : match.team1
    const otherteamRoute = `/teams/${otherTeamName}`

    return (
        <div className='MatchSmallCard'>
            <h3>
                <i> vs </i>
                <Link to={otherteamRoute}>
                    {otherTeamName}
                </Link>
            </h3>
            <p>{match.matchWinner} won by {match.resultMargin} {match.result}</p>
            <hr />
        </div >
    )
}

export default MatchSmallCard
