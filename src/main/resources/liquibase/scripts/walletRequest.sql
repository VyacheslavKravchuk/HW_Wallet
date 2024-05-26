CREATE TABLE wallet_request
(
    transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     status varchar(10) CHECK (status IN ('DEPOSIT', 'WITHDRAW')),
    balance    int  NOT NULL,
    wallet_id UUID,
    FOREIGN KEY (wallet_id) REFERENCES wallet_registered(wallet_id)
)