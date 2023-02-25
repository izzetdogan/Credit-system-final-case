import { NextPage } from "next";
import React, { useEffect, useState } from "react";
import AddUserForm from "../../../components/forms/addUserUser";
import { IUser, UserFormInterface } from "../../../types/typing";
import axios from "axios";
import { toast } from "react-toastify";

const EditUser: NextPage = ({ id }: any) => {
  const [user, setUser] = useState<IUser | null>(null);
  const [updateUser, setUpdateUser] = useState<UserFormInterface | null>();

  console.log(user);
  useEffect(() => {
    const getUser = async (id: string) => {
      try {
        console.log("id", id);
        const { data } = await axios.get(
          `http://localhost:8080/api/v1/users/${id}`
        );

        const userform: UserFormInterface = {
          nationalId: data.nationalId,
          firstname: data.firstname,
          lastname: data.lastname,
          birthdate: data.birthdate,
          deposit: data.deposit,
          monthlyIncome: data.monthlyIncome,
          phoneNumber: data.phoneNumber,
        };
        setUpdateUser(userform);
        setUser(data);
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error.response?.data.message);
        } else {
          console.log("Something went wrong");
        }
      }
    };
    getUser(id);
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUpdateUser({ ...updateUser, [e.target.name]: value });
  };

  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();

    try {
      const res = await axios.put(
        `http://localhost:8080/api/v1/app/${id}`,
        updateUser
      );
      console.log(res.data);
      toast.success("Record Updated");
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
        <AddUserForm
          user={user}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
        />
      </div>
    </div>
  );
};

export default EditUser;

export async function getServerSideProps({ params }: any) {
  return {
    props: { id: params.id },
  };
}
