import React, { useEffect, useState } from "react";
import styles from "../user.module.css";
import { IUser } from "../../../types/typing";
import { useRouter } from "next/router";
import axios from "axios";
import { NextPage } from "next";
import Link from "next/link";

const ShowUser: NextPage = ({ id }: any) => {
  const [user, setUser] = useState<IUser | null>(null);

  useEffect(() => {
    const getOneUserByID = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/v1/users/${id}`);
        setUser(res.data);
      } catch (error) {
        console.log(error);
      }
    };

    getOneUserByID();
  }, []);

  console.log("deneme", user);

  return (
    <div className="container">
      <div className={styles.titleUser}>
        <Link href="/user/edit/[id]" as={`/user/edit/${user?.id} `}>
          <button className="contentAddButton">Edit User</button>
        </Link>
      </div>
      {user && (
        <table className={styles.table}>
          <tr>
            <td>
              <strong>National ID</strong>
            </td>
            <td>{user.nationalId}</td>
          </tr>
          <tr>
            <td>
              <strong>Firstname</strong>
            </td>
            <td>{user.firstname}</td>
          </tr>
          <tr>
            <td>
              <strong>Lastname</strong>
            </td>
            <td>{user.lastname}</td>
          </tr>
          <tr>
            <td>
              <strong>Credit Result</strong>
            </td>
            <td
              className={
                user.credit.creditResult === "REJECTED"
                  ? styles.redText
                  : styles.greenText
              }
            >
              {user.credit.creditResult}
            </td>
          </tr>
          <tr>
            <td>
              <strong>Credit Limit</strong>
            </td>
            <td>
              <span>{user.credit.creditLimit}</span>
            </td>
          </tr>
          <tr>
            <td>
              <strong>CreditScore</strong>
            </td>

            <td>{user.creditScore}</td>
          </tr>
          <tr>
            <td>
              <strong>MonthlyIncome</strong>
            </td>

            <td>{user.monthlyIncome}</td>
          </tr>
          <tr>
            <td>
              <strong>Deposit</strong>
            </td>

            <td>{user.deposit}</td>
          </tr>
          <tr>
            <td>
              <strong>PhoneNumber</strong>
            </td>

            <td>{user.phoneNumber}</td>
          </tr>
          <tr>
            <td>
              <strong>BirthDay</strong>
            </td>

            <td>{user.birthdate}</td>
          </tr>
          <tr>
            <td>
              <strong>Record</strong>
            </td>

            <td>{user.credit.updatedAt}</td>
          </tr>
        </table>
      )}
    </div>
  );
};

export default ShowUser;

export async function getServerSideProps({ params }: any) {
  return {
    props: { id: params.id },
  };
}
