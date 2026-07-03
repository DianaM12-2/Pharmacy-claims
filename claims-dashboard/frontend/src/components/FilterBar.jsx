const FILTERS = [
  { value: "all",      label: "All Claims" },
  { value: "approved", label: "Approved" },
  { value: "pending",  label: "Pending" },
  { value: "flagged",  label: "Flagged" },
  { value: "rejected", label: "Rejected" },
];

export default function FilterBar({ value, onChange }) {
  return (
    <div className="filter-bar">
      {FILTERS.map(f => (
        <button
          key={f.value}
          className={`filter-btn ${value === f.value ? "active" : ""}`}
          onClick={() => onChange(f.value)}
        >
          {f.label}
        </button>
      ))}
    </div>
  );
}
