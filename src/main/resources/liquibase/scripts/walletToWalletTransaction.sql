CREATE TABLE wallet_to_wallet_transaction
(
    wallet_to_wallet_transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    email      VARCHAR(60) NOT NULL,
    amount int  NOT NULL,
    wallet_id UUID,
    FOREIGN KEY (wallet_id) REFERENCES wallet_registered(wallet_id)
)