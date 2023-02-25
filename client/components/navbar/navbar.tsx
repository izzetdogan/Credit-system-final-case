import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import AppBar from "@mui/material/AppBar";
import CssBaseline from "@mui/material/CssBaseline";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import Link from "next/link";
import { useEffect, useState } from "react";
import axios from "axios";
import { IUser } from "../../types/typing";

const drawerWidth = 240;

export interface IResponse {
  message: string;
  success: boolean;
}

export default function ClippedDrawer() {
  const [natId, setNatId] = useState("");
  const [birth, setBirth] = useState("");
  const [user, setUser] = useState<IUser | null>(null);
  const [message, setMessage] = useState<IResponse | null>(null);

  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();
    setUser(null);

    try {
      const res = await axios.get(
        `http://localhost:8080/api/v1/users/search?natId=${natId}&date=${birth}`
      );

      console.log(res.data);
      setUser(res.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log(error.response);
        setMessage(error.response?.data);
      } else {
        console.log("Something went wrong");
      }
    }
  };

  return (
    <Box sx={{ display: "flex", marginRight: "100px" }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ zIndex: theme => theme.zIndex.drawer + 1 }}
      >
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            <Link href="/user">Show All User </Link>
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: "auto" }}>
          <List>
            {user ? (
              <>
                <Link
                  className="searchInfo"
                  href="/user/show/[id]"
                  as={`/user/show/${user.id}`}
                >
                  <span>{user.nationalId}</span> ---
                  <span> {user.firstname}</span>
                </Link>
              </>
            ) : (
              <>{message ? <>{message.message}</> : <></>}</>
            )}
          </List>
          <Divider />
          <List>
            <ListItem>
              <form>
                <input type="text" onChange={e => setNatId(e.target.value)} />
                <input type="date" onChange={e => setBirth(e.target.value)} />
                <button type="submit" onClick={handleSubmit}>
                  {" "}
                  button{" "}
                </button>
              </form>
            </ListItem>
          </List>
        </Box>
      </Drawer>
    </Box>
  );
}
