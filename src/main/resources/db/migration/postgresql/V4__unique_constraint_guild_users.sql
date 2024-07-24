ALTER TABLE guild_users
ADD CONSTRAINT unique_guild_user UNIQUE (guild_id, user_id);