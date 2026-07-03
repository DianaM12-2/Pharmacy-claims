# Pharmacy Claims Dashboard

A full-stack web application for real-time pharmacy claims monitoring and fraud detection. Built with **React** (frontend) and **Python/Flask** (backend), connected via REST API.

![Dashboard Preview](docs/preview.png)

---

## Features

- Live claims dashboard with analytics cards
- Submit, view, filter, and delete claims
- Fraud detection — flags high-value and duplicate claims automatically
- Filter by status (All / Approved / Pending / Flagged / Rejected)
- Responsive layout, clean modern UI
- Docker Compose for one-command startup
- GitHub Actions CI for both frontend and backend

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React 18, Vite, CSS |
| Backend | Python 3.12, Flask 3 |
| API | RESTful JSON API |
| Containerization | Docker + Docker Compose |
| CI/CD | GitHub Actions |

---

## Getting Started

### Option 1 — Docker Compose (recommended)
```bash
git clone https://github.com/DianaM12-2/claims-dashboard.git
cd claims-dashboard
docker-compose up
```
- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:5000`

### Option 2 — Run separately

**Backend:**
```bash
cd backend
pip install -r requirements.txt
python run.py
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

---

## Project Structure

```
claims-dashboard/
├── frontend/           # React app (Vite)
│   ├── src/
│   │   ├── components/ # ClaimsTable, AnalyticsCards, ClaimForm, FilterBar
│   │   ├── services/   # API client (fetch wrapper)
│   │   └── styles/     # CSS
├── backend/            # Flask API
│   ├── app/
│   │   ├── routes/     # claims.py, factcheck.py, health.py
│   │   ├── services/   # ClaimsService, FactCheckService
│   │   └── models/     # PharmacyClaim dataclass
└── docker-compose.yml
```

---

## Author

**Diana Martinez** — [GitHub](https://github.com/DianaM12-2) · [LinkedIn](https://linkedin.com/in/diana-martinez-s)
