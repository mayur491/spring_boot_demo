import { React, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import MatchDetailCard from '../components/MatchDetailCard'
import MatchSmallCard from '../components/MatchSmallCard'

const TeamPage = () => {

    const [team, setTeam] = useState({ matches: [] })

    const { teamName } = useParams()

    // Fetch Team Matches
    useEffect(
        () => {
            const fetchTeam = async () => {
                const response = await fetch(`http://localhost:8080/team/${teamName}`)
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
            <h1>{team.teamName}</h1>
            {
                <MatchDetailCard
                    teamName={team.teamName}
                    match={team.matches[0]}
                />
            }
            {
                team
                    .matches
                    .slice(1)
                    .map(match => <MatchSmallCard
                        teamName={team.teamName}
                        match={match}
                    />)
            }
        </div>
    )
}

export default TeamPage
