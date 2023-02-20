import React from 'react'

import TextField from "@mui/material/TextField";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

export default function CommonRegForms() {
    const [dobvalue, setDOBValue] = React.useState(null);
    return (
        
            <div className="form">
                <div className="subheading">Name</div>
                <div className="name" >
                    <TextField
                        label="First Name"
                        size="small"
                    />
                    <TextField
                        label="Last Name"
                        size="small" />
                </div>
                <div className="subheading">Contact Information</div>
                <div className="contact">
                    <TextField
                        label="Email"
                        size="small" />
                    <TextField
                        label="Phone"
                        size="small" />
                </div>
                <div className="subheading">Address Information</div>
                <div className="address">

                    <TextField
                        fullWidth
                        label="Address" size="small" />
                    <TextField
                        label="City" size="small" />
                    <TextField
                        label="Province" size="small" />
                    <TextField
                        label="Postal Code" size="small" />
                    <TextField
                        label="Country" size="small" />

                </div>
                <div className="subheading">Verification Information</div>
                <div className="verification">

                    <TextField
                        fullWidth
                        label="Government ID" size="small" />

                    <label>Upload profile picture: </label>
                    <input type="file" />

                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                            label="Date of Birth"
                            value={dobvalue}
                            size="small"
                            helperText="Input your date of birth."
                            onChange={(newValue) => {
                                setDOBValue(newValue);
                            }}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </LocalizationProvider>




                </div>



                <button className="btn" type="submit">
                    Register
                </button>           
            </div>

      
    )
}