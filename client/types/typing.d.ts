export interface IUser {
  id: string;
  nationalId: number;
  firstname: string;
  lastname: string;
  monthlyIncome: string;
  deposit: number;
  phoneNumber: string;
  birthdate: string;
  creditScore: number;
  credit: ICredit;
  createdAt: string;
  updatedAt: string;
}

export interface ICredit {
  id: string;
  creditResult: string;
  creditLimit: number;
  createdAt: string;
  updatedAt: string;
}

export interface UserFormInterface {
  nationalId?: number;
  firstname?: string;
  lastname?: string;
  monthlyIncome?: string;
  deposit?: number;
  phoneNumber?: string;
  birthdate?: string;
}

export interface IResponseToast {
  success: boolean;
  message: string;
}
