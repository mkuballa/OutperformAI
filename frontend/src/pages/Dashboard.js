import React, { useState } from 'react';
import Header from '../components/Header';
import PortfolioChart from '../components/PortfolioChart';
import HoldingsTable from '../components/HoldingsTable';
import AddStockModal from '../components/AddStockModal';

const Dashboard = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handleStockAdded = () => {
    setIsModalOpen(false);
    setRefreshTrigger(prev => prev + 1);
  };

  return (
    <div className="container mx-auto p-4 sm:p-6 lg:p-8">
      <Header />
      <PortfolioChart refreshTrigger={refreshTrigger} />
      <HoldingsTable refreshTrigger={refreshTrigger} />
      <button
        onClick={() => setIsModalOpen(true)}
        className="fixed bottom-10 right-10 bg-blue-600 text-white w-16 h-16 rounded-full shadow-lg flex items-center justify-center text-3xl hover:bg-blue-700 transition-colors"
      >
        +
      </button>
      <AddStockModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} onStockAdded={handleStockAdded} />
    </div>
  );
};

export default Dashboard;