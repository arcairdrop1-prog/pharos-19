import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MultiProtocolAggregator {

    // Protocol Interface (Requirement 01)
    public interface ProtocolVault {
        String getName();
        double getApy();
        boolean isAvailable();
        String getPosition(String user);
        String prepareDeposit(double amount);
        String prepareWithdraw(double amount);
    }

    // Protocol A Implementation
    public static class PharosVaultA implements ProtocolVault {
        boolean available;

        public PharosVaultA(boolean available) {
            this.available = available;
        }

        @Override public String getName() { return "Pharos High-Yield Vault A"; }
        @Override public double getApy() { return 12.5; }
        @Override public boolean isAvailable() { return available; }
        @Override public String getPosition(String user) { return "Vault A Position: 1,500 USDC staked."; }
        @Override public String prepareDeposit(double amount) { return "ETH_SIGN_TYPED_DATA: Deposit " + amount + " USDC to Vault A"; }
        @Override public String prepareWithdraw(double amount) { return "ETH_SIGN_TYPED_DATA: Withdraw " + amount + " USDC from Vault A"; }
    }

    // Protocol B Implementation
    public static class PharosVaultB implements ProtocolVault {
        boolean available;

        public PharosVaultB(boolean available) {
            this.available = available;
        }

        @Override public String getName() { return "Pharos Stable Vault B"; }
        @Override public double getApy() { return 8.2; }
        @Override public boolean isAvailable() { return available; }
        @Override public String getPosition(String user) { return "Vault B Position: 3,200 USDC staked."; }
        @Override public String prepareDeposit(double amount) { return "ETH_SIGN_TYPED_DATA: Deposit " + amount + " USDC to Vault B"; }
        @Override public String prepareWithdraw(double amount) { return "ETH_SIGN_TYPED_DATA: Withdraw " + amount + " USDC from Vault B"; }
    }

    private final List<ProtocolVault> vaults = new ArrayList<>();

    public MultiProtocolAggregator(boolean vaultAAvailable, boolean vaultBAvailable) {
        vaults.add(new PharosVaultA(vaultAAvailable));
        vaults.add(new PharosVaultB(vaultBAvailable));
    }

    // Requirement 02: Supports queries (positions, rates)
    public void queryAllPositions(String user) {
        System.out.println("=== QUERY ALL POSITIONS & RATES ===");
        for (ProtocolVault vault : vaults) {
            if (vault.isAvailable()) {
                System.out.printf("- [%s] APY: %.1f%% | %s\n", vault.getName(), vault.getApy(), vault.getPosition(user));
            } else {
                System.out.printf("- [%s] Status: ❌ UNAVAILABLE (Skipped gracefully)\n", vault.getName());
            }
        }
    }

    // Requirement 03 & 04: Routes to best venue, explains choice, handles unavailability without breaking
    public String routeAndPrepareDeposit(double amount) {
        System.out.println("\n=== ROUTING DEPOSIT REQUEST ($" + amount + ") ===");
        ProtocolVault bestVault = null;
        double bestApy = -1.0;

        for (ProtocolVault vault : vaults) {
            if (!vault.isAvailable()) {
                System.out.println("⚠️ Warning: " + vault.getName() + " is currently offline/unavailable. Skipping.");
                continue;
            }
            if (vault.getApy() > bestApy) {
                bestApy = vault.getApy();
                bestVault = vault;
            }
        }

        if (bestVault == null) {
            return "ERROR: All connected protocol vaults are currently unavailable.";
        }

        // Requirement 03: Explains why it chose the venue
        System.out.printf("💡 Routing Decision: Selected [%s] because it offers the highest available APY (%.1f%%).\n", 
                bestVault.getName(), bestVault.getApy());

        // Requirement 02: Action prepared for signature
        return bestVault.prepareDeposit(amount);
    }

    public static void main(String[] args) {
        // Scenario 1: All vaults available
        System.out.println("--- Scenario 1: Normal Operation ---");
        MultiProtocolAggregator aggregator = new MultiProtocolAggregator(true, true);
        aggregator.queryAllPositions("0xUser123");
        String txPayload = aggregator.routeAndPrepareDeposit(1000.0);
        System.out.println("Prepared Tx Payload: " + txPayload);

        // Scenario 2: Protocol A unavailable (Requirement 04)
        System.out.println("\n--- Scenario 2: Protocol A Unavailable (Fault Tolerance Test) ---");
        MultiProtocolAggregator degradedAggregator = new MultiProtocolAggregator(false, true);
        degradedAggregator.queryAllPositions("0xUser123");
        String fallbackTxPayload = degradedAggregator.routeAndPrepareDeposit(1000.0);
        System.out.println("Prepared Tx Payload: " + fallbackTxPayload);
    }
}
