import React, { useState } from 'react'
import Header from '../components/Header'
import { useForm, Controller } from "react-hook-form";
import TextField from "@mui/material/TextField";
import axios from "../utils/MyAxios";
import Constants from "../Constants";
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
        first_name: '',
        last_name: '',
        email: '',
        phone: '',
        address: '',
        city: '',
        province: '',
        postal_code: '',
        country: ''
    });

    React.useEffect(() => {
        // Get user info from token
        axios.get(Constants.API_USER_INFO_FROM_TOKEN).then((response) => {
            const user = response.data;
            console.log(user);
            setProfileInfo(user);
        });
    }, []);

    // Function to handle changes to the input fields
    const handleInputChange = (event) => {
        const { first_name, value } = event.target;

        setProfileInfo((prevState) => ({
            ...prevState,
            [first_name]: value,
        }));
    };

    // Function to handle form submission
    const onFormSubmit = (event) => {
        console.log(profileInfo);
        // TODO: profileInfo paylod should contain following data.
        /* {
            "first_name": "John",
            "last_name": "Doe",
            "phone": null,
            "email": "rhs.yopmail.com@yopmail.com",
            "gov_id_url": null,
            "profile_pic_url": null,
            "dob": null,
            "address": null,
            "city": null,
            "province": null,
            "postal_code": null,
            "country": null,
            "is_activated": null,
            "is_verified": true,
        } */
        /* axios.put(Constants.API_USER, profileInfo).then((response) => {
            console.log(response);
        }); */
        // Code to update user profile information goes here
    };

    const { register, handleSubmit, control, watch, formState: { errors } } = useForm();

    return (
        <div className="editprofile">
            <Header title="Edit profile details" />
            <form className="form" onSubmit={handleSubmit(onFormSubmit)}>
                <div className="subheading">Name</div>
                <div className="name" >
                    <Controller
                        name="first_name"
                        control={control}
                        defaultValue={profileInfo.first_name}
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
                        name="last_name"
                        control={control}
                        defaultValue={profileInfo.last_name}
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
                        defaultValue={profileInfo.email}
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
                        defaultValue={profileInfo.phone}
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
                        defaultValue={profileInfo.address}
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
                        defaultValue={profileInfo.city}
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
                        defaultValue={profileInfo.province}
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
                        defaultValue={profileInfo.postal_code}
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
                        defaultValue={profileInfo.country}
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
                <div>
                    <label>Upload profile picture: </label>
                    <input type="file" />
                </div>
                <input className="btn" type="submit" />
            </form>
        </div>
    )
}

// FormSubmit:   https://blog.logrocket.com/react-hook-form-complete-guide/