export interface Employee {
    userId?: string;
    firstName: string;
    lastName: string;
    personalNumber: string,
    email: string;
    phoneNumber: string;
    street: string,
    zip: string,
    city: string,
    jobTitle: string;
    parentCompany?: string;
    startDate: Date;
    endDate?: Date;
    employeeStatus?: string;
	systemStatus?: string;
}