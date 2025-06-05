# 📡 Java Peer-to-Peer Encrypted Messaging

A simple Java-based peer-to-peer messaging system using **AES encryption** for secure communication. This project demonstrates how two Java programs — `Peer` and `Receiver` — can send and receive encrypted messages either **on the same device** or **across devices over a LAN**.

---

## 🔐 Key Features

* ✉️ **Encrypted Messaging** using AES (Advanced Encryption Standard)
* 🖥️ **Local Communication** on the same computer
* 🌐 **Cross-Device Communication** over a LAN
* 🔁 **Symmetric Key Encryption** with a shared 16-character key
* 📥 Simple input/output terminal-based interface

---

## 🧰 Requirements

* Java Development Kit (JDK 8+)
* Recommended IDE: **NetBeans**

  * Easy terminal access
  * Simple project setup

---

## 🏁 Setup & Execution (Using NetBeans)

### 1. 📂 Project Setup

* Launch NetBeans and create a **New Java Project** (e.g., `MessagingApp`)
* Inside the `src` folder, add two Java files:

  * `Peer.java` (for sending)
  * `Receiver.java` (for receiving)
* Use the appropriate code:

  * For same device use → from `SameDevice/`
  * For different device use → from `DifferentDevices/`

---

### 2. 🖥️ Same Device Messaging (Localhost)

* **Step 1**: Run `Receiver.java`

  > Starts listening on `localhost:5000`

* **Step 2**: Run `Peer.java`

  > Prompts you to type a message

* ✅ The message is AES-encrypted, sent to `localhost:5000`, decrypted, and displayed by the receiver.

---

### 3. 🌐 Cross-Device Messaging (LAN)

> Ensure both computers are connected to the **same Wi-Fi or LAN**.

* **On Computer A** (Receiver):

  * Run `Receiver.java` (listens on `port 5000`)

* **On Computer B** (Peer):

  * Run `Peer.java`
  * Enter:

    * The message to send
    * **IP address** of Computer A
    * **Port** number (5000)

* ✅ The message is AES-encrypted, sent over the network, and decrypted by the receiver.

🔍 **Find IP Address of Receiver (Computer A):**

* Windows: `ipconfig`
* macOS/Linux: `ifconfig` or `ip a`

---

## 🔒 How Encryption Works

* Uses **AES (Advanced Encryption Standard)**
* Requires a **shared 16-character key**
* Message flow:

  1. Sender encrypts message using AES
  2. Message is sent via socket to receiver
  3. Receiver decrypts message using the same AES key

> ⚠️ Both sender and receiver must use the same key

---

## 🔧 Common Issues & Fixes

| Issue                     | Solution                                         |
| ------------------------- | ------------------------------------------------ |
| 🔥 Firewall blocking port | Allow Java or open port 5000                     |
| 📡 Devices not connected  | Ensure both are on the same LAN                  |
| ❌ Wrong IP/Port           | Double-check the receiver’s IP and use port 5000 |
| 🔑 Mismatched AES Key     | Ensure both files use the same 16-char key       |

---

