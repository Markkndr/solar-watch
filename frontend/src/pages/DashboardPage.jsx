import { useEffect, useState } from 'react'
import { apiRequest } from '../api/client'

export default function DashboardPage() {
  const [city, setCity] = useState('Budapest')
  const [date, setDate] = useState(new Date().toISOString().slice(0, 10))
  const [result, setResult] = useState(null)
  const [history, setHistory] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  async function loadHistory() {
    try {
      const data = await apiRequest('/api/solar/history')
      setHistory(data)
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    loadHistory()
  }, [])

  async function handleSearch(event) {
    event.preventDefault()
    setLoading(true)
    setError('')
    try {
      const params = new URLSearchParams({ city, date })
      const data = await apiRequest(`/api/solar?${params.toString()}`)
      setResult(data)
      await loadHistory()
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="dashboard-grid">
      <section className="card">
        <h2>Search solar data</h2>
        <form onSubmit={handleSearch} className="form-grid">
          <input value={city} onChange={(e) => setCity(e.target.value)} placeholder="City" />
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
          <button type="submit" disabled={loading}>{loading ? 'Loading...' : 'Search'}</button>
        </form>
        {error && <p className="error-text">{error}</p>}
        {result && (
          <div className="result-box">
            <h3>{result.city}, {result.country}</h3>
            <p><strong>Date:</strong> {result.date}</p>
            <p><strong>Sunrise:</strong> {result.sunrise}</p>
            <p><strong>Sunset:</strong> {result.sunset}</p>
            <p><strong>Day length:</strong> {result.dayLengthSeconds} seconds</p>
            <p><strong>Coordinates:</strong> {result.latitude}, {result.longitude}</p>
            <p><strong>Timezone:</strong> {result.timezone}</p>
          </div>
        )}
      </section>

      <section className="card">
        <h2>Recent history</h2>
        <div className="history-list">
          {history.length === 0 && <p>No searches yet.</p>}
          {history.map((item) => (
            <article key={item.id} className="history-item">
              <strong>{item.city}, {item.country}</strong>
              <span>{item.date}</span>
              <span>Sunrise: {item.sunrise}</span>
              <span>Sunset: {item.sunset}</span>
            </article>
          ))}
        </div>
      </section>
    </div>
  )
}
