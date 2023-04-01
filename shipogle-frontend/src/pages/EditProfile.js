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
            console.log("user data inside use effect")
            console.log(user);
            setProfileInfo(user);
            console.log("userprofile set: ");
            console.log(profileInfo);
            console.log(profileInfo.first_name);
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
        console.log("profile info on onFormSubmit")
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
        axios
        .post(Constants.API_LOGIN, {
            phone: profileInfo.phone,
            
            
        })
    };



    return (
        <div className="editprofile">
            <Header title="Edit profile details" />
            <form className="form" onSubmit={onFormSubmit}>
                <div className="subheading">Name</div>
                <div className="name" >
                    <TextField
                        label="First Name"
                        value={profileInfo.first_name}
                        size="small"
                        onChange={handleInputChange}
                        disabled={true}
                    />
                    <TextField
                        label="Last Name"
                        value={profileInfo.last_name}
                        size="small"
                        onChange={handleInputChange}
                        disabled={true}
                    />
                </div>
                <div className="subheading">Contact Information</div>
                <div className="contact">
                <TextField
                        label="Email"
                        type="email"
                        value={profileInfo.email}
                        size="small"
                        onChange={handleInputChange}
                        disabled={true}
                    />
                <TextField
                        label="Phone Number"
                        value={profileInfo.phone}
                        size="small"
                        onChange={handleInputChange}
                      
                    />
                
        
                </div>
                <div className="subheading">Address Information</div>
                <div className="address">
                <TextField
                        label="Address"
                        type="text"
                        value={profileInfo.address}
                        size="small"
                        onChange={handleInputChange}
                        
                    />
                    <TextField
                        label="City"
                        type="text"
                        value={profileInfo.city}
                        size="small"
                        onChange={handleInputChange}
                        
                    />
                    <TextField
                        label="Province"
                        type="text"
                        value={profileInfo.province}
                        size="small"
                        onChange={handleInputChange}
                        
                    />
                    <TextField
                        label="Postal Code"
                        type="text"
                        value={profileInfo.postal_code}
                        size="small"
                        onChange={handleInputChange}
                        
                    />
                    <TextField
                        label="Country"
                        type="text"
                        value={profileInfo.country}
                        size="small"
                        onChange={handleInputChange}
                        
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