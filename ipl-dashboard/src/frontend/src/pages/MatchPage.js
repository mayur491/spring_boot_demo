import { React, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import MatchDetailCard from '../components/MatchDetailCard'
import MatchSmallCard from '../components/MatchSmallCard'

const MatchPage = () => {

    const [matches, setMatches] = useState([])
    const { teamName, year } = useParams()

    // Fetch Matches for a Team
    useEffect(
        () => {
            const fetchMatches = async () => {
                const response = await fetch(`http://localhost:8080/team/${teamName}/matches?year=${year}`)
                const data = await response.json()
                if (data === null || data.success === false) return null
                else if (data.success === true) return setMatches(data.matches)
            }
            fetchMatches()
        }, [teamName]
    )

    // If matches not found
    if (!matches) {
        return <h2>
            <i>Matches NOT found!!!</i>
        </h2>
    }

    return (
        <div className='MatchPage'>
            <h1>Match Page Yoo</h1>
            {
                matches
                    .map(match => <MatchDetailCard
                        teamName={teamName}
                        match={match}
                    />)
            }
        </div>
    )
}

export default MatchPage
