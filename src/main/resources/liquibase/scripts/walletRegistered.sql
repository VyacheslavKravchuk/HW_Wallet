CREATE TABLE wallet_registered
(
    wallet_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    balance    int  NOT NULL,
    email      VARCHAR(60) NOT NULL,
    password      VARCHAR(60) NOT NULL

)