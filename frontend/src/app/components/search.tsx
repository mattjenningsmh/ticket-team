"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { SearchIcon } from 'lucide-react'

export default function Search() {
  const [query, setQuery] = useState("")
  const router = useRouter()

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    router.push(`/search?q=${encodeURIComponent(query)}`)
  }

  return (
    <form onSubmit={handleSearch} className="w-full max-w-2xl mx-auto">
      <div className="relative group">
        <SearchIcon className="absolute left-4 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground group-hover:text-primary transition-colors" />
        <input
          type="text"
          className="w-full px-12 py-4 text-lg bg-background border-2 border-input rounded-2xl shadow-sm focus:outline-none focus:ring-2 focus:ring-ring focus:border-input transition-all dark:bg-secondary"
          placeholder="Search events, artists, or venues..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button
          type="submit"
          className="absolute right-3 top-1/2 -translate-y-1/2 px-6 py-2 bg-primary text-primary-foreground rounded-xl font-medium hover:bg-primary/90 transition-colors"
        >
          Search
        </button>
      </div>
    </form>
  )
}