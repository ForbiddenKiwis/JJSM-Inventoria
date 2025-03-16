# Android Java Application: System Management

## Overview
This is an **Android Java application** designed as an **e-commerce platform** with Firebase integration. The application provides functionality for searching, managing, and purchasing products. It offers features tailored for three types of users: **Buyers**, **Admins**, and **Product Publishers**. Additionally, users can access the app in two modes: **Logged-in mode** and **Guest mode**.

---

## Features

### General Features
- Firebase integration for real-time database and authentication.
- Firebase Authentication (password/email or Google)
- Product search by **name**, **ID**, and **description**.
- Secure login system using Firebase Authentication.
- **Guest Mode** for users to view products without logging in.

### User Types and Permissions

#### 1. **Buyers**
- Search for products.
- Add products to **cart** for purchase.
- Add products to **Wishlist**.
- Purchase products.
- Cannot add, edit, or delete any products.

#### 2. **Admins**
- Full access to the application.
- Delete products if deemed **malicious** or **infringing** on terms and policies.
- Manage all product data and user activities.

#### 3. **Product Publishers**
- Add new products to the platform.
- Edit details of products they have published.
- Delete their own products.
- Cannot modify or delete products added by other publishers.

---

## Access Modes

### 1. **Logged-in Mode**
- Users can log in using their credentials.
- Logged-in users can access all features based on their role (Buyer, Admin, or Product Publisher).

### 2. **Guest Mode**
- Users can browse and view products.
- **Restrictions in Guest Mode**:
  - Cannot add products to the cart.
  - Cannot add products to the Wishlist.
  - Cannot purchase products.

---

## Technologies Used
- **Programming Language**: Java
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Frontend**: Android XML for UI design
- **Backend**: Java with Firebase SDK

---

## Key Functionalities

### Product Search
- Search functionality allows users to find products using:
  - **Name**
  - **ID**
  - **Description**

### Buyer Functionalities
- View product details.
- Add products to the **cart** for future purchases.
- Add products to the **Wishlist** for later reference.
- Complete purchases through the cart.

### Admin Functionalities
- Monitor all products and user activities.
- Delete products that violate the platform’s **terms and policies**.

### Product Publisher Functionalities
- Add new products, including details such as:
  - **Name**
  - **Description**
  - **Price**
  - **Image**
- Edit and delete products they have published.
- Cannot modify or delete products added by other publishers.

### Guest Access
- Browse and view products.
- Cannot add products to the cart or Wishlist.
- Cannot purchase products.

---

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/android-system-management.git
   ```

2. Open the project in **Android Studio**.

3. Configure Firebase:
   - Add your project to Firebase Console.
   - Download the `google-services.json` file and place it in the `app/` directory.

4. Sync the project with Gradle files.

5. Run the application on an emulator or a physical Android device.

---

## Future Improvements
- Add support for multiple payment gateways.
- Implement notifications for product updates.
- Enhance UI/UX for better user interaction.
- Add analytics for product performance tracking.

---

## License
This project is licensed under the MIT License. See the LICENSE file for more details.

---

## Contributing
Contributions are welcome! Please fork the repository and create a pull request for any feature enhancements or bug fixes.

---

## Contact
For inquiries or support, please contact:
- **Name**: Jerwinson Flores Cunanan
- **Email**: jerwinson.cunanan19@gmail.com

