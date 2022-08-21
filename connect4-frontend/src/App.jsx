import React, { useState } from 'react'
import HomePage from './pages/HomePage'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './App.css'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomePage thing="i am root!"/>} />
        <Route path='/fart' element={<HomePage thing="fart"/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
