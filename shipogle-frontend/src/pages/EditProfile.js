import React, { useState } from 'react'
import Header from '../components/Header'
import { useForm, Controller } from "react-hook-form";
import TextField from "@mui/material/TextField";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

export default function EditProfile() {
  const [dobvalue, setDOBValue] = React.useState(null);
  const [status, setStatus] = useState(false);
  //govt ID
  const govIDValidation = () => {
    //add condition for verification here
    setStatus(true);

}
   
  // State to hold user profile information
  const [profileInfo, setProfileInfo] = useState({
    firstName: 'John',
    lastName: "Doe",
    email: 'john@gmail.com',
    
  });
  // Function to handle changes to the input fields
  const handleInputChange = (event) => {
   const { firstName, value } = event.target; 
   
    setProfileInfo((prevState) => ({
      ...prevState,
      [firstName]: value,
    }));
  };
  // Function to handle form submission
  const handleChange = (event) => {
    event.preventDefault();
    // Code to update user profile information goes here
  };
  const { register, handleSubmit, control, watch, formState: { errors } } = useForm();
  return (
    <div className="editprofile">
      <Header
        title="Edit profile details" />
                <form className="form" onSubmit={handleSubmit(handleInputChange)}>
            <div className="subheading">Name</div>
            <div className="name" >
                <Controller
                    name="firstName"
                    control={control}
                    defaultValue= {profileInfo.firstName}
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
                            value={profileInfo.lastName}
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
            <div>git 

                <label>Upload profile picture: </label>
                <input type="file" />

      

            </div>
           


            <input className="btn" type="submit" />
        </form>

    </div>

  )
}
