import React from 'react'
import Header from '../../components/Header'
import shipogleLogo from '../../assets/shipogleLogo.png'
import { Link } from 'react-router-dom'
import StarRating from '../../components/StarRating';



export default function Feedback() {

    const [showMsg, setShowMsg] = React.useState(0);

    const submit = (e) => {
        setShowMsg(prevShowMsg => 1);
    
    };
    return (
        <div className="feedback-issue-page">
            <Header
                title="S H I P O G L E"
                info="tagline"
            />
            <center>
                <img alt="logo" src={shipogleLogo} width="200px" height="200px">

                </img>
            </center>
            <div className="feedback-issue-box">
                <h4>We value your feedback.</h4>
                <br></br>
                <StarRating />
                <br>
                </br>
                <br></br>
                <div>
                    <button className="btn" type="submit" onClick={submit}>
                        Submit
                    </button>
                    {showMsg === 1 ? <p>Thank you for your feedback!</p> : <p></p>}
                </div>


            </div>


        </div>
    )
}

