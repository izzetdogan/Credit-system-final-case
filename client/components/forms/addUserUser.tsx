import styles from "./form.module.css";
import "react";

import { ChangeEventHandler, MouseEventHandler } from "react";
import { IUser } from "../../types/typing";

interface IProps {
  handleSubmit?: MouseEventHandler;
  handleChange?: ChangeEventHandler<HTMLInputElement>;
  user?: IUser | null;
}
const AddUserForm = ({ handleSubmit, handleChange, user }: IProps) => {
  return (
    <div>
      <h1 className={styles.addContentTitle}>
        {" "}
        {user ? <span>Edit User</span> : <span>Add User</span>}
      </h1>

      <form className={styles.addContentForm}>
        <div className={styles.addContentItem}>
          <label>National-ID</label>
          <input
            name="nationalId"
            type="number"
            required
            onChange={handleChange}
            placeholder={user ? (user.nationalId as any) : "National-Id"}
          />
        </div>

        <div className={styles.addContentItem}>
          <label>Name</label>
          <input
            required
            name="firstname"
            type="text"
            onChange={handleChange}
            placeholder={user ? user.firstname : "Firtsname"}
          />
        </div>
        <div className={styles.addContentItem}>
          <label>Lastname</label>
          <input
            onChange={handleChange}
            required
            name="lastname"
            type="text"
            placeholder={user ? user.lastname : "Lastname"}
          />
        </div>
        <div className={styles.addContentItem}>
          <label>Phone-Number</label>
          <input
            required
            onChange={handleChange}
            name="phoneNumber"
            type="text"
            placeholder={user ? user.phoneNumber : "Number"}
          />
        </div>

        <div className={styles.addContentItem}>
          <label>Monthly-Income</label>
          <input
            onChange={handleChange}
            required
            name="monthlyIncome"
            type="number"
            placeholder={user ? user.monthlyIncome : "Monthly-Income"}
          />
        </div>
        <div className={styles.addContentItem}>
          <label>Deposit</label>
          <input
            onChange={handleChange}
            name="deposit"
            type="number"
            placeholder={user ? (user.deposit as any) : "Deposit(Optional)"}
          />
        </div>

        <div className={styles.addContentItem}>
          <label>BirthDate</label>
          <input onChange={handleChange} name="birthdate" type="date" />
        </div>

        <div className={styles.addContentItem}>
          <button onClick={handleSubmit} className={styles.addContentButton}>
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddUserForm;
