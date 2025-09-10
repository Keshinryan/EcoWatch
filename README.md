# 🌧️ Rainfall Prediction App (Android)

An Android application that predicts **rainfall probability** based on **temperature, humidity, and wind speed** using a **Random Forest Classifier** trained on **BMKG (Indonesia Meteorological, Climatological, and Geophysical Agency)** data.  

The system also includes an integrated **Berita & Edukasi** section where admins can publish news and educational content, while users can access them directly from the app.

---

## ✨ Features

### 🌦️ Rainfall Prediction
- Input **temperature**, **humidity**, and **wind speed**  
- Predict rainfall using a **Random Forest model** trained on **BMKG data (2010–2020, ~100k samples)**  
- Output: **Rain / No Rain (Curah Hujan)**  

### 👥 User Roles
- **Admin**
  - 🔐 Login  
  - ➕➖✏️ CRUD **Berita** (News)  
  - ➕➖✏️ CRUD **Edukasi** (Educational content)  
- **User**
  - 📖 Read **Berita** and **Edukasi**  

---

## 🛠️ Tech Stack

- **Android (Java / Kotlin)** – Mobile development  
- **Random Forest Classifier** – Machine learning model for rainfall prediction  
- **Python (Scikit-learn)** – Model training (offline)  
- **SQLite / Firebase / MySQL (optional)** – Data storage for users, Berita, Edukasi  
- **REST API (Node.js / Express)** *(if backend is used)*  

---

## 📊 Dataset

- Source: **BMKG (Indonesia)**  
- Years: **2010 – 2020**  
- Size: ~**100k records**  
- Features:
  - 🌡️ Temperature  
  - 💧 Humidity  
  - 🍃 Wind Speed  
- Target: **Rainfall (Curah Hujan)** – categorical (Rain / No Rain)  

---

## 🚀 Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/RainfallPredictionApp.git
   cd RainfallPredictionApp
   ```

2. Open the project in **Android Studio**.

3. Sync Gradle to install dependencies.

4. Run the app on an emulator or a physical Android device.

---

## 📂 Project Structure

```
RainfallPredictionApp/
│── app/
│   ├── java/com/example/rainfallprediction/
│   │   ├── activities/        # Activities (Login, Prediction, Admin CRUD, etc.)
│   │   ├── adapters/          # RecyclerView Adapters
│   │   ├── models/            # Data models (User, Berita, Edukasi, Prediction)
│   │   ├── network/           # API or DB helpers
│   │   └── utils/             # Utility classes
│   └── res/                   # Layouts, Drawables, Values (UI)
│── model/                     # Trained Random Forest model (.pkl or .json export)
│── README.md
```

---

## 🔑 Core Functionalities

### Prediction
- Uses **Random Forest Classifier**  
- Pre-trained offline on BMKG dataset  
- Model integrated into Android app (via JSON export or TensorFlow Lite conversion)

### Authentication
- Admin login with JWT / Firebase Auth / Local DB  
- User access for reading content only  

### Berita & Edukasi
- Admin CRUD interface  
- User-friendly reader interface  

---

## 📸 Screenshots (Optional)

_Add some screenshots of your app UI here (Login, Prediction, Berita, Edukasi)._  

---

## 🤝 Contributing

Contributions are welcome! Please fork this repository and submit a pull request.

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

Developed by **Jason Patrick**  
Dataset: [BMKG (2010–2022)](https://www.kaggle.com/datasets/greegtitan/indonesia-climate)  
Model: **Random Forest Classifier**  
