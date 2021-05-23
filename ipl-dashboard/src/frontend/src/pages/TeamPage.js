import { React, useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import MatchDetailCard from '../components/MatchDetailCard'
import MatchSmallCard from '../components/MatchSmallCard'
import './TeamPage.css'
import { PieChart } from 'react-minimal-pie-chart';

const TeamPage = () => {

    const [team, setTeam] = useState({ matches: [] })

    const { teamName } = useParams()

    // Fetch Team Matches
    useEffect(
        () => {
            const fetchTeam = async () => {
                const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/team/${teamName}`)
                const data = await response.json()
                if (data === null || data.success === false) return null
                else if (data.success === true) return setTeam(data.team)
            }
            fetchTeam()
        }, [teamName]
    )

    // If team OR teamName not found
    if (!team || !team.teamName) {
        return <h2>
            <i>Team '{teamName}' NOT found!!!</i>
        </h2>
    }

    return (
        <div className='TeamPage'>
            {/* Row 1 starts */}
            <div className='team-name-section'>
                <h1 className='team-name'>{team.teamName}</h1>
            </div>
            <div className='win-loss-section'>
                Wins / Losses
                <PieChart
                    data={[
                        { title: 'Losses', value: team.totalMatches - team.totalWins, color: '#cc0033' },
                        { title: 'Wins', value: team.totalWins, color: '#00aa66' }
                    ]}
                />
            </div>
            {/* Row 1 ends */}

            {/* Row 2 starts */}
            <div className='match-detail-section'>
                <h2>Latest Matches</h2>
                {
                    <MatchDetailCard
                        teamName={team.teamName}
                        match={team.matches[0]}
                    />
                }
            </div>
            {/* Row 2 ends */}

            {/* Row 3 starts */}
            {
                team
                    .matches
                    .slice(1)
                    .map(match =>
                        // <div className='match-small-section'>
                        <MatchSmallCard
                            key={match.id}
                            teamName={team.teamName}
                            match={match}
                        />
                        // </div>
                    )
            }
            <div className='more-link'>
                <Link to={`./${teamName}/matches/${process.env.REACT_APP_DATA_END_YEAR}`}>
                    More {'>'}
                </Link>
            </div>
            {/* Row 3 ends */}

        </div>
    )
}

export default TeamPage
