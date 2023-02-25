import { DeleteOutline, DeleteOutlineOutlined } from "@mui/icons-material";
import { Box } from "@mui/system";
import type { NextPage } from "next";
import Link from "next/link";
import styles from "./user.module.css";
import { DataGrid, GridColDef, GridValueGetterParams } from "@mui/x-data-grid";
import { IUser } from "../../types/typing";
import { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

const UserHome: NextPage = () => {
  const [users, setUsers] = useState<IUser[]>([]);

  useEffect(() => {
    const getAllUser = async () => {
      try {
        const res = await axios.get<[IUser]>(
          `http://localhost:8080/api/v1/users`
        );
        setUsers(res.data);
      } catch (error) {
        console.log(error);
      }
    };

    getAllUser();
  }, []);

  const deleteUser = async (id: string) => {
    try {
      axios.delete(`http://localhost:8080/api/v1/users/${id}`);
      setUsers(users.filter(u => u.id !== id));
      toast.info("Record is Deleted");
    } catch (error) {
      console.log(error);
    }
  };

  const columns: GridColDef[] = [
    { field: "nationalId", headerName: "NatId", width: 120 },
    { field: "firstname", headerName: "First name", width: 100 },
    { field: "lastname", headerName: "lastname", width: 100 },
    { field: "phoneNumber", headerName: "Phone", width: 150 },
    { field: "birthdate", headerName: "Birthdate", width: 100 },
    {
      field: "monthlyIncome",
      headerName: "monthly-Income",
      type: "number",
      width: 110,
    },
    { field: "creditScore", headerName: "Credit-Score", width: 150 },
    {
      field: "action",
      headerName: "Action",
      width: 150,
      renderCell: params => {
        return (
          <>
            <Link href="/user/show/[id]" as={`/user/show/${params.row.id}`}>
              <button className="contentListEdit">Show</button>
            </Link>
            <DeleteOutlineOutlined
              className={styles.deleteIcon}
              onClick={() => deleteUser(params.row.id)}
            />
          </>
        );
      },
    },
  ];

  return (
    <div className="container">
      <div className="test">
        <span>
          <h2> All User</h2>
        </span>
        <Link href="/user/add-user">
          <button className="contentAddButton">New User</button>
        </Link>
      </div>

      <Box sx={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={users}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          disableSelectionOnClick
          experimentalFeatures={{ newEditingApi: true }}
        />
      </Box>
    </div>
  );
};

export default UserHome;
