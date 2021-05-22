import { React, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import MatchDetailCard from '../components/MatchDetailCard'
import YearSelector from '../components/YearSelector'
import './MatchPage.css'

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
        }, [teamName, year]
    )

    // If matches not found
    if (!matches) {
        return <h2>
            <i>Matches NOT found!!!</i>
        </h2>
    }

    return (
        <div className='MatchPage'>
            <div className='year-selector'>
                <h3>Select Year</h3>
                <YearSelector
                    teamName={teamName}
                />
            </div>
            <div>
                <h1 className='page-heading'>
                    {teamName} matches in {year}
                </h1>
                {
                    matches
                        .map(match => <MatchDetailCard
                            teamName={teamName}
                            match={match}
                        />)
                }
            </div>
        </div>
    )
}

export default MatchPage
