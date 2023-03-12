import React, { useState } from 'react';
import { useForm, Controller } from "react-hook-form";
import { useNavigate } from 'react-router-dom';
import TextField from "@mui/material/TextField";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import verified from '../assets/accept.png';
import Constants from '../Constants';
const axios = require('axios');


export default function CommonRegForms() {
    const [dobvalue, setDOBValue] = React.useState(null);

    const { register, handleSubmit, control, watch, formState: { errors } } = useForm();

    let path = "/registration/success";

    let navigate = useNavigate();

    const navUser = () => {
        navigate(path);
    }
    //https://stackoverflow.com/questions/58257648/how-do-i-display-text-on-button-click-in-react-js#:~:text=If%20you%20want%20to%20display,function%20to%20trigger%20the%20window.&text=However%2C%20if%20you%20need%20to,a%20state%20to%20manage%20that.&text=If%20you%20need%20to%20toggle,the%20onButtonClickHandler%20function%20to%20this.

    const [status, setStatus] = useState(false);

    const govIDValidation = () => {
        //add condition for verification here
        setStatus(true);

    }
    return (


        <form className="form" onSubmit={handleSubmit(navUser)}>
            <div className="subheading">Name</div>
            <div className="name" >
                <Controller
                    name="firstName"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="First Name"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'First name required' }}
                />
                <Controller
                    name="lastName"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Last Name"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Last name required' }}
                />
            </div>
            <div className="subheading">Contact Information</div>
            <div className="contact">
                <Controller
                    name="email"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Email ID"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Email ID is required' }}
                />
                <Controller
                    name="phone"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Phone Number"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Phone No. is required' }}
                />
            </div>
            <div className="subheading">Address Information</div>
            <div className="address">

                <Controller
                    name="address"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Address"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Address  is required' }}
                />
                <Controller
                    name="city"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="City"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'City  is required' }}
                />
                <Controller
                    name="province"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Province"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Province  is required' }}
                />
                <Controller
                    name="postal code"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Postal Code"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Postal Code  is required' }}
                />
                <Controller
                    name="country"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Country"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Country  is required' }}
                />

            </div>
            <div className="subheading">Verification Information</div>
            <div className="verification">

                <Controller
                    name="govtID"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Govt ID"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Government ID  is required' }}
                />

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

                <div className="btn checkVeri" onClick={() => govIDValidation()}>Verify</div>
                <div className="veriStatus">{status ?
                    <p id="success">Successful Verification! </p> :
                    <p id="fail">Not verified! Enter again!</p>}</div>


            </div>
            <div className="subheading">Set Password</div>
            <div className="setpwd">
                <Controller
                    name="setpwd"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Set Password"
                            type="password"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Password  is required' }}
                />
                <Controller
                    name="confirmpwd"
                    control={control}
                    defaultValue=""
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <TextField
                            label="Confirm Password"
                            type="password"
                            value={value}
                            size="small"
                            onChange={onChange}
                            error={!!error}
                            helperText={error ? error.message : null}
                        />
                    )}
                    rules={{ required: 'Password should match' }}
                />

            </div>



            <input className="btn" type="submit" />
        </form>


    )
}