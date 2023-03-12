import React, { useState } from 'react';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import Header from '../components/Header';
import { FormControl, Menu } from '@mui/material';
import { FormHelperText } from '@mui/material';

import { Controller, useForm } from 'react-hook-form';
import TextField from "@mui/material/TextField";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

export default function SendItems() {
    const [mode, setMode] = useState("");
    const [packageType, setPackageType] = useState("");
    const [due, setDue] = useState(null);
    const [showRes, setShowRes] = useState(false);

    
    const { register, handleSubmit, control, watch, formState: { errors } } = useForm();

    return (
        <div className="sendform">
            <Header
                title="Send Items"
                info="Enter details about the package you want to send and get matched with deliverers." />
            <div className="send-del-form">
                <FormControl fullWidth size="small">

                    <InputLabel id="mode">Mode</InputLabel>
                    <Select
                        labelId="mode"
                        id="demo-simple-select"
                        value={mode}
                        label="Mode"
                        required
                        onChange={(e) => setMode(e.target.value)}
                    >
                        <MenuItem value={10}>Car</MenuItem>
                        <MenuItem value={20}>Airplane</MenuItem>
                        <MenuItem value={30}>Truck</MenuItem>
                    </Select>
                    <br></br>
                </FormControl>
                <FormControl fullWidth size="small">

                    <InputLabel id="pkgtype">Package</InputLabel>
                    <Select
                        labelId="pkgtype"
                        id="demo-simple-select"
                        required
                        value={packageType}
                        label="Mode"
                        onChange={(e) => setPackageType(e.target.value)}
                    >
                        <MenuItem value={10}>Documents and paperwork</MenuItem>
                        <MenuItem value={20}>Small packages and parcels</MenuItem>
                        <MenuItem value={30}>Medical supplies and equipment</MenuItem>
                        <MenuItem value={40}>Food and perishables</MenuItem>
                        <MenuItem value={50}>Retail products and merchandise</MenuItem>
                        <MenuItem value={60}>Industrial parts and equipment</MenuItem>
                        <MenuItem value={70}>Personal belongings and luggage</MenuItem>
                        <MenuItem value={80}>Fragile or valuable items</MenuItem>
                        <MenuItem value={90}>Hazardous materials</MenuItem>
                        <MenuItem value={100}>Oversized or bulky items</MenuItem>
                        <MenuItem value={400}>Other</MenuItem>
                    </Select>
                    <br></br>
                </FormControl>

                <div className="routeinfo">
                    <Controller
                        name="source"
                        control={control}
                        defaultValue=""
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <TextField
                                label="Source"
                                value={value}
                                size="small"
                                onChange={onChange}
                                error={!!error}
                                helperText={error ? error.message : null}
                            />
                        )}
                        rules={{ required: 'Source is required' }}
                    />
                    <Controller
                        name="destination"
                        control={control}
                        defaultValue=""
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <TextField
                                label="Destination"
                                value={value}
                                size="small"
                                onChange={onChange}
                                error={!!error}
                                helperText={error ? error.message : null}
                            />
                        )}
                        rules={{ required: 'Destination is required' }}
                    />

                    <Controller
                        name="weight"
                        control={control}
                        defaultValue=""
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <TextField
                                label="Package Weight (lbs)"
                                value={value}
                                size="small"
                                onChange={onChange}
                                error={!!error}
                                helperText={error ? error.message : null}
                            />
                        )}
                        rules={{ required: 'Weight is required' }}
                    />

                </div>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                        label="Date to deliver by"
                        value={due}
                        size="small"
                        helperText="Input your date of birth."
                        onChange={(newValue) => {
                            setDue(newValue);
                        }}
                        renderInput={(params) => <TextField {...params} />}
                    />

                </LocalizationProvider>
                <div><br></br></div>


                <button className="btn" type="submit" onClick={() => setShowRes(true)}>
                    Send my package!
                </button>


            </div>
            {showRes ? 
            <div className="matchRes">
                Following are the matches!
            </div> : <div></div>
}


        </div>
    )
}
