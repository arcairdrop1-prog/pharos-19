# Pharos Multi-Protocol Aggregator Agent

An advanced agent developed in **Java** for the Pharos Agent Arena that integrates multiple protocols and vaults behind a single interface, handling queries, intelligent rate-based routing, actions prepared for signature, and fault tolerance[cite: 14].

## 📋 Requirements Met

1. **Unified Interface (Req. 01):** Integrates at least 2 protocols or vaults behind one common interface[cite: 14].
2. **Queries & Actions (Req. 02):** Supports both queries (positions, rates) and actions (deposits or withdrawals prepared for signature)[cite: 14].
3. **Intelligent Routing (Req. 03):** Routes each request to the best venue and explains the reasoning behind the choice[cite: 14].
4. **Fault Tolerance (Req. 04):** Handles protocol unavailability gracefully without breaking the rest of the system[cite: 14].
5. **Architecture Write-up (Req. 05):** Modular and extensible architecture design[cite: 14].

## 🚀 How to Run

1. Make sure you have **Java JDK** installed.
2. Compile the file:
   ```bash
   javac MultiProtocolAggregator.java
