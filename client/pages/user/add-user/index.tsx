import React, { useState } from "react";
import AddUserForm from "../../../components/forms/addUserUser";
import { UserFormInterface } from "../../../types/typing";
import axios from "axios";
import { toast } from "react-toastify";
import { Router, useRouter } from "next/router";

const AddUser = () => {
  const [user, setUser] = useState<UserFormInterface | null>(null);
  const router = useRouter();
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
    console.log(user);
  };

  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();

    try {
      const res = await axios.post("http://localhost:8080/api/v1/app", user);
      console.log(res.data);
      toast.info(`Redireect to`);
      setTimeout(function () {
        router.push(`/user/show/${res.data.id}`);
      }, 2500);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        error.response?.data.message
          ? toast.error(error.response?.data.message)
          : toast.error("check fields");
      } else {
        console.log("Something went wrong");
        toast.warn("Something went Wrong");
      }
    }
  };
  return (
    <div className="container">
      <div>
        <AddUserForm handleChange={handleChange} handleSubmit={handleSubmit} />
      </div>
    </div>
  );
};

export default AddUser;
