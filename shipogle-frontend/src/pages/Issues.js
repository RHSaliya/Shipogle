import React from 'react'
import Header from '../../components/Header'
import shipogleLogo from '../../assets/shipogleLogo.png'
import { Link } from 'react-router-dom'
import StarRating from '../../components/StarRating';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';


export default function Issue() {

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
        <h4>Please enter your issue. We will get back to you shortly. Thank you for your patience.</h4>
        <br></br>
        <div>
          <Box
            component="form"
            sx={{
              '& .MuiTextField-root': { m: 1, width: '25ch' },
            }}
            noValidate
            autoComplete="off"
          >
            <div>
              <TextField
                id="outlined-multiline-flexible"
                label="Issue"
                style={{backgroundColor:"white",
                width:"100%"}}
                multiline
                maxRows={4}
              />
            </div>
          </Box>
        </div>
        <br>
        </br>
        <br></br>
        <div>
          <button className="btn" type="submit" onClick={submit}>
            Submit
          </button>
          {showMsg === 1 ? <p>We have registered your issue!</p> : <p></p>}
        </div>


      </div>


    </div>
  )
}

