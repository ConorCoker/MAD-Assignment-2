# MAD Assignment 2 – Order Receiver App

## Functionality Overview

This app allows users to create/browse a menu, search for items, and place orders. Main features include:

- **Menu Creation** with item name, descriptions, price and photo.
- **Menu Browsing** with category filters and search by text functionality.
- **Firebase Authentication** with **Google sign-in** or **email/password sign-in**.
- **Cloud Storage** for menu item images thanks to **Firebase Cloud Storage**.
- **Order Storage and retrieval** through **Firebase Firestore**.
- **Room DB** for storing menu items.

### Third-Party & Google APIs Used

- **Firebase Authentication** – Google Sign-In
- **Firebase Firestore** – Realtime database for orders
- **Firebase Cloud Storage** – Stores user uploaded images
- **Jetpack Compose** – UI framework
- **Hilt** – Dependency Injection
- **Coil** – Image loading
- **Material3** – UI components and Icons

---

## Relationships / Interactions

- **MenuItem**  -->  **MenuDao**  : Menu items are stored in a ROOM DB
- **Order**  -->  **MenuViewModel**  : Orders are stored on Firestore but accessed via the view model
- **MenuDao**  -->  **MenuViewModel**  : Menu items are stored in ROOM DB and accessed via the view model
- **MenuViewModel**  -->  **Menu**, **OrdersScreen**, **AddToMenuScreen**  : The three screens all feed and consume data from the view model
- **MenuItemRow**  -->  **MenuViewModel**  : Calls `addToOrder()`, `deleteMenuItem()` in the view model by tapping or swiping on the row

## 🛠️ Git

- **master** – Production branch.
- **dev** – Development branch.
- **release/v2.0.0** – Release branch for version v2.0.0.

---

## ✍️ Personal Statement

I always love working in Android, so I really enjoyed working on this assignment. It is also the final assignment that I will be 
submitting in college as I am now done my four years. Thank you very much! :)

---
