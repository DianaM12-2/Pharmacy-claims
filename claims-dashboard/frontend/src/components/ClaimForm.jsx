import { useState } from "react";

export default function ClaimForm({ onSubmit }) {
  const [form, setForm] = useState({ patient_name: "", medication: "", amount: "" });
  const [submitting, setSubmitting] = useState(false);

  function handleChange(e) {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (!form.patient_name || !form.medication || !form.amount) {
      alert("All fields are required.");
      return;
    }
    setSubmitting(true);
    await onSubmit({ ...form, amount: parseFloat(form.amount) });
    setSubmitting(false);
    setForm({ patient_name: "", medication: "", amount: "" });
  }

  return (
    <form className="claim-form" onSubmit={handleSubmit}>
      <h3>Submit New Claim</h3>
      <div className="form-row">
        <input name="patient_name" placeholder="Patient Name" value={form.patient_name} onChange={handleChange} required />
        <input name="medication"   placeholder="Medication"   value={form.medication}   onChange={handleChange} required />
        <input name="amount" type="number" placeholder="Amount ($)" value={form.amount} onChange={handleChange} min="0" step="0.01" required />
        <button type="submit" className="btn-primary" disabled={submitting}>
          {submitting ? "Submitting..." : "Submit Claim"}
        </button>
      </div>
    </form>
  );
}
