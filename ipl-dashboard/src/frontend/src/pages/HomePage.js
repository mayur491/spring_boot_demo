import { React, useEffect, useState } from 'react'
import TeamTile from '../components/TeamTile'
import './HomePage.css'

const HomePage = () => {

    const [teams, setTeams] = useState([])

    // Fetch Team Matches
    useEffect(
        () => {
            const fetchAllTeams = async () => {
                const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/team/`)
                const data = await response.json()
                if (data === null || data.success === false) return null
                else if (data.success === true) return setTeams(data.teams)
            }
            fetchAllTeams()
        }, []
    )

    return (
        <div className='HomePage'>

            <div className='header-section'>
                <h1 className='app-name'>CodeMayur IPL Dashboard</h1>
            </div>

            <div className='team-grid'>
                {
                    teams.map(
                        team =>
                            <TeamTile
                                key={team.id}
                                teamName={team.teamName}
                            />
                    )
                }
            </div>

        </div>
    )
}

export default HomePage
