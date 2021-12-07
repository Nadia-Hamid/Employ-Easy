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
    //imageUrl: string;
}

/*
    personalNumber: '860426-XXXX',
    photo: 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png'
    */