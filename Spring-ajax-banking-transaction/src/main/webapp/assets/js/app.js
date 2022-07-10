class App {

    static SweetAlert = class {
        static showSuspendConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Are you sure to suspend the selected customer?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#5a6268',
                confirmButtonText: 'Hell yeah!',
                cancelButtonText: 'Yeah, nah',
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'center',
                showConfirmButton: false,
                timer: 1500,
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
                timer: 2000,
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(t) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: t
            });
        }

        static showErrorAlert(t) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 3500,
                message: t
            });
        }
    }
}

class Customer {
    constructor(id, fullName, email, phone, address, balance, isDeleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isDeleted = isDeleted;
    }
}

class Deposit {
    constructor(id, customerId, transactionAmount, createdAt, createdBy) {
        this.id = id;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}

class Withdraw {
    constructor(id, customerId, transactionAmount, createdAt, createdBy) {
        this.id = id;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}

class Transfer {
    constructor(id, senderId, recipientId, transactionAmount, fee, feeAmount, createdAt, createdBy) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.transactionAmount = transactionAmount;
        this.fee = fee;
        this.feeAmount = feeAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}

class Sender extends Customer {
    constructor() {
        super();
    }
}

class Recipient extends Customer {
    constructor() {
        super();
    }
}