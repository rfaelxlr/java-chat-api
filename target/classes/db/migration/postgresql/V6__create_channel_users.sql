CREATE TABLE channel_users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   user_id BIGINT,
   channel_id BIGINT,
   CONSTRAINT pk_channel_users PRIMARY KEY (id)
);

ALTER TABLE channel_users ADD CONSTRAINT FK_CHANNEL_USERS_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channels (id);

ALTER TABLE channel_users ADD CONSTRAINT FK_CHANNEL_USERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE channel_users
ADD CONSTRAINT unique_channels_users_channel_users UNIQUE (channel_id, user_id);